<?php

namespace App\Http\Controllers\API\v1\BasicControllers;

use App\Http\Controllers\Controller;
use App\Http\Requests\Store\ScheduleStoreRequest;
use App\Http\Requests\Update\ScheduleUpdateRequest;
use App\Http\Resources\ScheduleResource;
use App\Models\Sports\Schedule;
use App\Services\SemaphoreService;
use Exception;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Log;

class ScheduleController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $schedule = Schedule::whereHas('banner', function ($query) {
                                $query->where('is_default', true);
                            })
                            ->whereNotNull(['date', 'time'], 'and')
                            ->with([
                                'firstTeam.player.basicInformation.gender',
                                'firstTeam.player.position',
                                'secondTeam.player.basicInformation.gender',
                                'secondTeam.player.position',
                                'winningTeam.player',
                                'sports',
                                'gameStatus'
                            ])
                            ->get();
            return ScheduleResource::collection($schedule);
    }

    public function show(Schedule $schedule)
    {
        $schedule->load(['firstTeam', 'secondTeam', 'winningTeam', 'sports', 'gameStatus']);
        return ScheduleResource::make($schedule);
    }
    /**
     * Store a newly created resource in storage.
     */
    public function store(ScheduleStoreRequest $request)
    {
        $schedule = Schedule::create($request->validated());
        $schedule->load(['firstTeam', 'secondTeam', 'winningTeam', 'sports', 'gameStatus']);
        return ScheduleResource::make($schedule);
    }


    /**
     * Update the specified resource in storage.
     */
    public function update(ScheduleUpdateRequest $request, Schedule $schedule)
    {
        try {

            $validated = $request->validated();
            return response()->json(['message' => 'This shit went here 123']);

            if (!empty($validated['referee_full_name'])) {
                $semaphore = new SemaphoreService();
                // Collect the coach phone numbers
                $coachNumbers = collect([
                    $schedule->firstTeam->coach->phone_no ?? '09219298620',
                    $schedule->secondTeam->coach->phone_no ?? '09219298620',
                ]);
        
                $playerNumbers = collect();
        
                // Loop through first team's players and collect phone numbers
                if($schedule->firstTeam){
                    foreach ($schedule->firstTeam->player as $player) {
                        if (isset($player->basicInformation)) {
                            $playerNumbers->push($player->basicInformation->phone_no);
                        }
                    }
                }

                if($schedule->secondTeam){
                    // Loop through second team's players and collect phone numbers
                    foreach ($schedule->secondTeam->player as $player) {
                        if (isset($player->basicInformation)) {
                            $playerNumbers->push($player->basicInformation->phone_no);
                        }
                    }
                }
                
                
                // Combine both coach and player numbers
                $numbers = $coachNumbers->merge($playerNumbers);
                
                // If you want to convert the collection to an array, you can do so
                $numbersArray = $numbers->toArray();
                $firstTeamTitle = $schedule->firstTeam->title ?? 'NO OPONENT';
                $secondTeamTitle = $schedule->secondTeam->title ?? 'NO OPONENT';
                $message = 'Testing: Your scheduled fight ' 
                        . $firstTeamTitle
                        . ' vs ' . $secondTeamTitle . ' is now set on '
                        .  $validated['date'] . ' at ' . $validated['time'] 
                        . '. You will have Mr/Mrs ' . ($validated['referee_full_name'] ?? $schedule->referee_full_name)
                        . ' as your Refferee. Please Dont reply to this message, Thank you!';
        
                $response = $semaphore->bulkSMS($numbersArray, $message);
                // return response()->json(['number'=> $numbersArray, 'message' => $message, 'semaphore' => $response]);
            }
        }catch (Exception $e){
            return response()->json([
                'error' => 'An error occurred while updating the schedule.',
                'message' => $e->getMessage(), // Include the error message for debugging
                'line' => $e->getLine(), // Include the line number
                'file' => $e->getFile() // Include the file name for context
            ], 500); // HTTP status code 500 for server errors
        }
        try{
            // return $request->validated();

            if ($schedule->is_pointing_system == 1) {
                return response()->json(['message' => 'Pointing system result updated']);
            } 
            return response()->json(['message' => 'This shit went here']);
            $result = $schedule->update($request->validated());

            if($request->winner_team_id == null){
                $schedule->load(['firstTeam', 'secondTeam', 'winningTeam', 'sports', 'gameStatus']);
                return ScheduleResource::make($schedule);
            } else {
                if($schedule->nextMatch != null){
                    if($schedule->nextMatch->next_match_id === null){
                        $bronzeSched = Schedule::where('sports_id' ,$validated['sports_id'])
                                                ->where('match_round', 'Bronze')->first();
                        $team = '';
                        if($request->winner_team_id === $request->first_team_id){
                            $team =  $request->second_team_id;
                        }else if ($request->winner_team_id === $request->second_team_id){
                            $team =  $request->first_team_id;
                        }
                        
                        if($bronzeSched->first_team_id === null){
                            $bronzeSched->first_team_id = $team;
                        }else{
                            $bronzeSched->second_team_id = $team;
                        }
                        $bronzeSched->save();
                    }

                    if($schedule->nextMatch->first_team_id == null){
                        $schedule->nextMatch->update(['first_team_id' => $request->winner_team_id]);
                    } else{
                        $schedule->nextMatch->update(['second_team_id' => $request->winner_team_id]);
                    } 
                }else if ($schedule->next_match_id === null){
                    if($request->match_round == "Bronze"){
                        if($request->winner_team_id == $request->first_team_id){
                            $schedule->firstTeam->update(['lib_award_id' => 3]);
                        }
                        else{
                            $schedule->secondTeam->update(['lib_award_id' => 3]);
                        }
                    }
                    else if($request->winner_team_id == $request->first_team_id){
                        $schedule->firstTeam->update(['lib_award_id' => 5]);
                        $schedule->secondTeam->update(['lib_award_id' => 4]);
                    } else if ($request->winner_team_id ==  $request->second_team_id){
                        $schedule->firstTeam->update(['lib_award_id' => 4]);
                        $schedule->secondTeam->update(['lib_award_id' => 5]);
                    }

                }else{

                }

            }
        }catch (Exception $e){
            return response()->json([
                'error' => 'An error occurred while updating the schedule.',
                'message' => $e->getMessage(), // Include the error message for debugging
                'line' => $e->getLine(), // Include the line number
                'file' => $e->getFile() // Include the file name for context
            ], 500); // HTTP status code 500 for server errors
        } finally {
            return response()->json(['message' => 'Data Updated', 'semaphore' => $response]);
        }
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(Schedule $schedule)
    {
        $schedule->delete();
        return response()->noContent();
    }
}
