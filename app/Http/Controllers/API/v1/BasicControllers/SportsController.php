<?php

namespace App\Http\Controllers\API\v1\BasicControllers;

use App\Http\Controllers\Controller;
use App\Http\Requests\Store\SportsStoreRequest;
use App\Http\Requests\Update\SportsUpdateRequest;
use App\Http\Resources\SportsResource;
use App\Models\Others\Banner;
use App\Models\Sports\Sports;
use Error;
use Exception;
use Illuminate\Support\Str;
use Illuminate\Http\Request;

class SportsController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $data = Sports::where('delete', false)->get();
        return SportsResource::collection($data);
    }

    /**
     * Show the form for creating a new resource.
     */

    /**
     * Store a newly created resource in storage.
     */
    public function store(SportsStoreRequest $request)
    {
        // Access the 'logo' file from the request
        // if ($request->hasFile('team.logo')) {
        $img = $request->file('logo'); // Correct way to access the file in an array

        // Get the file's extension
        $ext = $img->getClientOriginalExtension();

        // Generate a random filename
        $imageName = Str::random(20) . '.' . $ext;

        // Move the file to the 'image' directory
        $img->move(public_path('image'), $imageName);

        $response = $request->validated();
        // Store the URL or path in the database
        $response['logo'] = asset('image/' . $imageName);

    $sports = Sports::create($response);
        return SportsResource::make($sports);
    }

    /**
     * Display the specified resource.
     */
    public function show(Sports $sport)
    {
        $banner = Banner::where('is_default', true)->first();
        $relation = [
            'position', 
            'team' => function($query) use ($banner) {
                $query->where('banner', $banner->id)->get();
            }, 
            'team.coach',
            'team.college',
            'team.sports',
            'team.award',
            'team.player.basicInformation',
            'team.player.position',
            'schedule',
            'schedule.firstTeam', 
            'schedule.secondTeam', 
            'schedule.winningTeam', 
            'schedule.sports', 
            'schedule.gameStatus'
        ];
        $sport->load($relation);
        return SportsResource::make($sport);
    }


    /**
     * Update the specified resource in storage.
     */
    public function update(SportsUpdateRequest $request, Sports $sport)
    {
        $validated =
        $sport->update($request->validated());
        return SportsResource::make($sport);
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(Sports $sport)
    {
        try{
            $sport->delete();
        } catch (Exception $e){
            $sport->update(['delete' => true]);
        }
        return response()->noContent();
    }

    public function getPlayersFromSport(Sports $sport)
    {
        $banner = Banner::where('is_default', true)->value('id');
        $player = $sport->load(
                        [
                            'team.player.basicInformation.gender' ,
                            'team.player.position' , 
                            'team.player.team.college',
                            'team.player.status',
                        ])
                        ->team->where('banner', $banner)
                        ->flatMap->player;

        return response()->json($player);
    }
}
