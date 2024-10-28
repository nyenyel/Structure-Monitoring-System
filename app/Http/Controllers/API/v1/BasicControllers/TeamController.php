<?php

namespace App\Http\Controllers\API\v1\BasicControllers;

use App\Http\Controllers\Controller;
use App\Http\Requests\Store\TeamStoreRequest;
use App\Http\Requests\Update\TeamUpdateRequest;
use App\Http\Resources\TeamResource;
use App\Models\Sports\Schedule;
use App\Models\Sports\Team;
use App\Models\Users\BasicInformation;
use App\Models\Users\Player;
use Illuminate\Support\Str;
use Illuminate\Http\Request;

class TeamController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $teams = Team::with(['sports', 'college', 'award', 'coach', 'player.basicInformation.gender', 'player.position'])->get();
        return TeamResource::collection($teams);
    }


    /**
     * Store a newly created resource in storage.
     */
    public function store(TeamStoreRequest $request)
    {
        $validated = $request->validated();

        // Access the 'logo' file from the request
        // if ($request->hasFile('team.logo')) {
        $img = $request->file('team.logo'); // Correct way to access the file in an array

        // Get the file's extension
        $ext = $img->getClientOriginalExtension();

        // Generate a random filename
        $imageName = Str::random(20) . '.' . $ext;

        // Move the file to the 'image' directory
        $img->move(public_path('image'), $imageName);

        // Store the URL or path in the database
        $validated['team']['logo'] = asset('image/' . $imageName);
        // } 
        
        // Create a new BasicInformation record
        $basicInformation = BasicInformation::create($validated['info']);

        // Add the coach_id to the team data
        $validated['team']['coach_id'] = $basicInformation->id;

        // Create a new Team record
        $team = Team::create($validated['team']);

        $team->load(['sports', 'college', 'award', 'coach.gender']);

        return new TeamResource($team);
    }

    /**
     * Display the specified resource.
     */
    public function show(Team $team)
    {
        $team->load(['sports', 'college', 'award', 'coach.gender', 'player.basicInformation.gender', 'player.position', 'player.status']);
        return TeamResource::make($team);
    }


    /**
     * Update the specified resource in storage.
     */
    public function update(TeamUpdateRequest $request, Team $team)
    {
        $validated = $request->validated();
        $team->update($request['team']);
        $team->coach->update($validated['info']);
        $team->load(['sports', 'college', 'award', 'coach.gender']);
        return TeamResource::make($team);
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(Team $team)
    {
        Player::where('team_id', $team->id)->delete();
        Schedule::where('first_team_id', $team->id)->delete();
        Schedule::where('second_team_id', $team->id)->delete();
        Schedule::where('winner_team_id', $team->id)->delete();
        $team->delete();
        $team->coach->delete();
        return response()->noContent();
    }
}
