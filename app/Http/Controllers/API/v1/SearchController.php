<?php

namespace App\Http\Controllers\API\v1;

use App\Http\Controllers\Controller;
use App\Models\Sports\College;
use App\Models\Sports\Sports;
use App\Models\Sports\Team;
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
            ->get();
        } else if($validated['type'] === 'Sport'){
            $data = Sports::whereRaw('LOWER(venue) LIKE ?', ["%{$term}%"])
            ->orWhereRaw('LOWER(title) LIKE ?', ["%{$term}%"])
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
        }

        return response()->json($data);
    }
}
