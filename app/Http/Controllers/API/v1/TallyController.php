<?php

namespace App\Http\Controllers\API\v1;

use App\Http\Controllers\Controller;
use App\Http\Resources\TeamResource;
use App\Models\Others\Banner;
use App\Models\Sports\College;
use App\Models\Sports\Team;
use Illuminate\Http\Request;

class TallyController extends Controller
{
    public function tally(){
        $data = [];
        $banner = Banner::where('is_default', true)->first();
        
        $colleges = College::with(['team' => function ($query) use ($banner){
            $query->where('banner', $banner->id)->with('award');
        }])->get();
        // $colleges->load(['team.award']);
        foreach($colleges as $college){
            $name = $college->title . '('. $college->acronym.')';
            $teams = $college->team;
            $temp = 0;
            $goldCounter = 0;
            $silverCounter = 0;
            $bronzeCounter = 0;
            foreach($teams as $team){
                if($team->lib_award_id > 2){
                    if($team->lib_award_id == 3){
                        $temp += 1;
                        $bronzeCounter++;
                    }elseif($team->lib_award_id == 4){
                        $temp += 2;
                        $silverCounter++;

                    }elseif($team->lib_award_id == 5){
                        $temp += 3;
                        $goldCounter += $team->player->count();
                    }
                }
            }
            $data[] = [
                'name' => $name,
                'acronym'=> $college->acronym,
                'point' => $temp,
                'gold' => $goldCounter,
                'silver' => $silverCounter,
                'bronze' => $bronzeCounter,
                'logo' => $college->logo,
                'team' => TeamResource::collection($college->team)
            ];
        }
        return response()->json( ['data' =>$data]); 
    }
}
