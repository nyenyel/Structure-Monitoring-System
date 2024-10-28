<?php

namespace App\Http\Controllers\API\v1;

use App\Http\Controllers\Controller;
use App\Http\Resources\TeamResource;
use App\Models\Sports\College;
use App\Models\Sports\Team;
use Illuminate\Http\Request;

class TallyController extends Controller
{
    public function tally(){
        $data = [];
        $colleges = College::all();
        $colleges->load(['team.award']);
        foreach($colleges as $college){
            $name = $college->title . '('. $college->acronym.')';
            $teams = Team::where('college_id', $college->id)->get();
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
                        $goldCounter++;

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
        return json_encode( ['data' =>$data]); 
    }
}
