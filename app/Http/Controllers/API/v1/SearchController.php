<?php

namespace App\Http\Controllers\API\v1;

use App\Http\Controllers\Controller;
use App\Http\Resources\BannerResource;
use App\Http\Resources\UserRegistrationResource;
use App\Models\Others\Banner;
use App\Models\Sports\College;
use App\Models\Sports\Sports;
use App\Models\Sports\Team;
use App\Models\User;
use App\Models\Users\Player;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class SearchController extends Controller
{
    public function search(Request $request){

        $validated = $request->validate([
            'type' => 'required',
            'term' => 'required',
            'team_id' => 'sometimes|nullable',
            'college_id' => 'sometimes|nullable',
        ]);

        $data = null;
        $term = strtolower($validated['term']);
        $teamID = strtolower($validated['team_id']);

        if($validated['type'] === 'College'){
            $data = College::whereRaw('LOWER(acronym) LIKE ?', ["%{$term}%"])
            ->orWhereRaw('LOWER(title) LIKE ?', ["%{$term}%"])
            ->where('delete', false)
            ->get();
        } else if($validated['type'] === 'Sport'){
            $data = Sports::whereRaw('LOWER(venue) LIKE ?', ["%{$term}%"])
            ->orWhereRaw('LOWER(title) LIKE ?', ["%{$term}%"])
            ->where('delete', false)
            ->get();
        } else if($validated['type'] === 'team'){
            $data = Team::with('sports')
                        ->where('college_id', $validated['college_id']) // Filter by team_id
                        ->whereRaw('LOWER(title) LIKE ?', ["%{$term}%"])
                        ->get();
        } else if($validated['type'] === 'player'){
            $data = Player::with('basicInformation')
                            ->where('team_id', $validated['team_id']) // Filter by team_id
                            ->whereRaw('LOWER(student_no) LIKE ?', ["%{$term}%"]) // Filter by student_no term, case-insensitive
                            ->get();
        } else if($validated['type'] === 'user'){
            $data = User::whereHas('basicInformation', function ($query) use ($term) {
                $query->whereRaw('LOWER(first_name) LIKE ?', ["%{$term}%"])
                        ->orWhereRaw('LOWER(middle_name) LIKE ?', ["%{$term}%"])
                        ->orWhereRaw('LOWER(last_name) LIKE ?', ["%{$term}%"]);
                })->get();
            $data->load(['basicInformation', 'role']);
            return UserRegistrationResource::collection($data);
        } else if($validated['type'] === 'banner'){
            $data = Banner::whereRaw('LOWER(title) LIKE ?', ["%{$term}%"])
                            ->orWhereRaw('LOWER(year) LIKE ?', ["%{$term}%"])
                            ->get();
            return BannerResource::collection($data);

        }

        return response()->json($data);
    }
}
