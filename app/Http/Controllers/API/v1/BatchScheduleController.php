<?php

namespace App\Http\Controllers\api\v1;

use App\Http\Controllers\Controller;
use App\Http\Resources\ScheduleResource;
use App\Models\Others\Banner;
use App\Models\Sports\Schedule;
use Exception;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Log;

class BatchScheduleController extends Controller
{
    public function batchSchedule(Request $request)
    {
        // return response()->json(['data' => $request->all()]);
        // DB::beginTransaction();
        $newData = '';
        try {
            $banner = Banner::where('is_default', true)->first();
            $predictedNumOfMatch = 0;
            $numOfTeams = $request->sports['num_of_teams'];
            // Adjusting numOfTeams based on the input
            if ($numOfTeams == 3) {
                $numOfTeams = 4;
            } elseif ($numOfTeams > 4 && $numOfTeams < 8) {
                $numOfTeams = 8;
            } elseif ($numOfTeams > 8 && $numOfTeams < 16) {
                $numOfTeams = 16;
            }

            $predictedNumOfMatch = $numOfTeams - 1;
            $isNotEven = ($request->sports['num_of_teams'] % 2) == 1;

            $numberOfRounds = $this->calculateRounds($numOfTeams);
            
            $tempNextMatchID = [];
            $counter = 1;
            $index = 0;
            $anotherCounter = 0;
            $isNotDone = true;
            $numOfMatch = 1;
            $temp = 1;
            $teams = $request->team;
            
            $template = [
                'date' => null,
                'time' => null,
                'match_no' => '',
                'match_round' => '',
                'next_match_id' => null,
                'first_team_id' => null,
                'second_team_id' => null,
                'winner_team_id' => null,
                'sports_id' => $request->sports['id'],
                'banner' => $banner->id,
                'lib_game_statuses_id' => 1,
                'referee_full_name' => ''
            ];
            
            $newData = $template;
            
            $result = ['message' => 'success'];
            $tempData = false;
            
            if($predictedNumOfMatch === 1){
                $newData['first_team_id'] = $teams['team-id-0'];
                $newData['second_team_id'] = $teams['team-id-1'];
                $newData['next_match_id'] = null;
                $newData['match_no'] = $numOfMatch;
                $newData['match_round'] = "Round " . $numberOfRounds;
                
                // Log::info("Creating match entry with first team only", ["newData" => $newData]);
                $numOfMatch += 1;
                Schedule::create($newData);
                return response()->json($result);
            }

            for ($x = $predictedNumOfMatch; $x > 0; $x--) {
                $countInTwosMain = $counter % 2;

                if ($x <= $numOfTeams / 2) {

                    
                    if ($isNotEven) {
                        $tempData = $x <= ($request->sports['num_of_teams'] / 2) + 0.5;
                    } else {
                        $tempData = $x <= ($request->sports['num_of_teams'] / 2);
                    }

                    if ($tempData) {
                        if ($counter == 4) {
                            // Swap logic
                            $hold = $tempNextMatchID[1];
                            $tempNextMatchID[1] = $tempNextMatchID[2];
                            $tempNextMatchID[2] = $hold;
                            $numberOfRounds--;
                        }

                        $tempCounterForTeams = 1;
                        $isRunned = true;
                        Log::info("Teams data for scheduling", ["teams" => $teams]);

                        foreach ($teams as $data) {
                            // Handle scheduling logic
                            if (isset($request->sports['num_of_teams']) && 
                                $request->sports['num_of_teams'] > 3 && 
                                $request->sports['num_of_teams'] < 8 && 
                                $isRunned && 
                                $request->sports['num_of_teams'] === 8) {
                                
                                $hold = $tempNextMatchID[1];
                                $tempNextMatchID[1] = $tempNextMatchID[2];
                                $tempNextMatchID[2] = $hold;
                                $isRunned = false;
                            }

                            try {
                                $countInTwos = $temp % 2;

                                if ($countInTwos == 1) {
                                    $newData['first_team_id'] = $data;

                                    if ($isNotEven && $tempCounterForTeams == $request->sports['num_of_teams']) {
                                        $newData['second_team_id'] = null;
                                        $newData['next_match_id'] = $tempNextMatchID[$index];
                                        $newData['match_no'] = $numOfMatch;
                                        $newData['match_round'] = "Round " . $numberOfRounds;

                                        Log::info("Creating match entry with first team only", ["newData" => $newData]);
                                        $numOfMatch += 1;
                                        Schedule::create($newData);
                                    }
                                } else {
                                    $newData['second_team_id'] = $data;
                                    $newData['next_match_id'] = $tempNextMatchID[$index];
                                    $newData['match_no'] = $numOfMatch;
                                    $newData['match_round'] = "Round " . $numberOfRounds;

                                    Log::info("Creating match entry with both teams", ["newData" => $newData]);
                                    $numOfMatch += 1;
                                    Schedule::create($newData);
                                    $newData = $template; // Reset to template
                                    $anotherCounter++;
                                    if ($anotherCounter == 2) {
                                        $anotherCounter = 0;
                                        $index++;
                                    }
                                }

                                $temp++;
                                $tempCounterForTeams++;
                            } catch (Exception $e) {
                                Log::error("Error processing team data", [
                                    'error' => $e->getMessage(),
                                    'team_data' => $data,
                                    'teams' => $teams
                                ]);

                                return json_encode([
                                    'Error' => $e->getMessage(),
                                    'sentData' => $teams
                                ]);
                            }
                        }
                        return json_encode($result);
                    } else {
                        $newData['match_no'] = $x;
                        $newData['match_round'] = "Round " . $numberOfRounds;
                        $newData['next_match_id'] = $tempNextMatchID[$index];
                        $schedule = Schedule::create($newData);
                        $tempNextMatchID[] = $schedule->id;
                    }
                } else {
                    if ($counter == 1) {
                        $newData['match_round'] = "Bronze";
                        $newData['match_no'] = $x + 1;
                        $scheduleB = Schedule::create($newData);

                        $newData['match_round'] = "Round " . $numberOfRounds;
                        $newData['match_no'] = $x;
                        $schedule = Schedule::create($newData);
                        $newData['next_match_id'] = $scheduleB->id;

                        $tempNextMatchID[] = $schedule->id;


                        $numberOfRounds--;
                    } else if ($counter > 1 && $counter < 4) {
                        $newData['match_no'] = $x;
                        $newData['match_round'] = "Round " . $numberOfRounds;
                        $newData['next_match_id'] = $tempNextMatchID[$index];
                        $schedule = Schedule::create($newData);
                        $tempNextMatchID[] = $schedule->id;
                    } else {
                        if ($counter == 4) {
                            $numberOfRounds--;
                            $hold = $tempNextMatchID[1];
                            $tempNextMatchID[1] = $tempNextMatchID[2];
                            $tempNextMatchID[2] = $hold;
                        }
                        $newData['match_no'] = $x;
                        $newData['match_round'] = "Round " . $numberOfRounds;
                        $newData['next_match_id'] = $tempNextMatchID[$index];
                        $schedule = Schedule::create($newData);
                        $tempNextMatchID[] = $schedule->id;

                        if ($counter == 7) {
                            // More logic for swapping and round adjustments
                            $hold = $tempNextMatchID[3];
                            $tempNextMatchID[3] = $tempNextMatchID[6];
                            $tempNextMatchID[6] = $hold;

                            $hold = $tempNextMatchID[4];
                            $tempNextMatchID[4] = $tempNextMatchID[5];
                            $tempNextMatchID[5] = $hold;
                            $numberOfRounds--;
                        }
                    }
                }
                if ($countInTwosMain == 1 && $counter != 1) {
                    $index++;
                }

                $newData = $template; // Reset to template
                $counter++;
            }

            DB::commit(); // Log successful commit
            Log::info('Successfully committed batch schedule processing', ['result' => $result]);
            return json_encode($result);
        
        } catch (\Exception $e) {
            DB::rollBack();
            Log::error('Error during batch schedule processing', [
                'error_message' => $e->getMessage(),
                'trace' => $e->getTraceAsString(),
                'current_data' => $newData
            ]);
            return response()->json(['error' => $e->getMessage()]);
        }
    }


    public function scheduledMatch($sportId){
        try{
            $banner = Banner::where('is_default', true)->first();
            $records = Schedule::whereNotNull(['date','time'], 'and' )
                            ->whereNull(['winner_team_id'])
                            ->where('sports_id', $sportId)
                            ->where('banner', $banner->id)
                            ->get();
            $records->load(['firstTeam', 'secondTeam', 'winningTeam', 'sports', 'gameStatus']);

            return ScheduleResource::collection($records);

        } catch (Exception $e) {
            return json_encode(['error' => $e->getMessage()]);
        }
    }
    private function calculateRounds($numberOfTeams) {
        if ($numberOfTeams < 2) {
            return 0; // No rounds needed if fewer than 2 teams
        }
        // Calculate the number of rounds needed
        return ceil(log($numberOfTeams, 2));
    }
}


// <?php

// namespace App\Http\Controllers\api\v1;

// use App\Http\Controllers\Controller;
// use App\Http\Resources\ScheduleResource;
// use App\Models\Sports\Schedule;
// use Exception;
// use Illuminate\Http\Request;

// class BatchScheduleController extends Controller
// {
//     public function batchSchedule(Request $request)
//     {
//         try {
//             $predictedNumOfMatch = 0;
//             $numOfTeams = 0;
//             if($request->sports['num_of_teams'] == 3){

//             }
//             $predictedNumOfMatch = $request->sports['num_of_teams'] - 1;


//             $numberOfRounds = $this->calculateRounds($request->sports['num_of_teams']);
//             $tempNextMatchID = [];
//             $counter = 1;
//             $index = 0;
//             $anotherCounter = 0;
//             $isNotDone = true;
//             $numOfMatch = 1;
//             $temp = 1;
//             $teams = $request->team;
//             $template = [
//                 'date' => null,
//                 'time' => null,
//                 'match_no' => '',
//                 'match_round' => '',
//                 'next_match_id' => null,
//                 'first_team_id' => null,
//                 'second_team_id' => null,
//                 'winner_team_id' => null,
//                 'sports_id' => $request->sports['id'],
//                 'lib_game_statuses_id' => 1,
//             ];
    
//             $newData = $template;
    
//             $result = ['message' => 'success'];
    
//             for ($x = $predictedNumOfMatch; $x > 0 ; $x--) {
//                 $countInTwosMain = $counter % 2;

//                 if ($x <= $request->sports['num_of_teams']/2) {

//                     if($counter == 4 ){
//                         $hold = $tempNextMatchID[1];
//                         $tempNextMatchID[1] = $tempNextMatchID[2];
//                         $tempNextMatchID[2] = $hold;
//                     }

//                     // foreach ($teams as $data) {
//                     //     $countInTwos = $temp % 2;
//                     //     if ($countInTwos == 1) {
//                     //         $newData['first_team_id'] = $data;
//                     //     } else {
//                     //         $newData['second_team_id'] = $data;
//                     //         $newData['match_no'] = $numOfMatch;
//                     //         $newData['match_round'] = "Round " . $numberOfRounds;
//                     //         $newData['next_match_id'] = $tempNextMatchID[$index];
//                     //         $numOfMatch += 1;
//                     //         Schedule::create($newData);
//                     //         $newData = $template; // Reset to template
//                     //         $anotherCounter++;
//                     //         if($anotherCounter == 2){
//                     //             $anotherCounter = 0;
//                     //             $index++;
//                     //         }
//                     //     }
//                     //     $temp++;
//                     // }
                    
//                     foreach ($teams as $data) {
//                         $countInTwos = $temp % 2;
                        
//                         // Check if we're at the last team and it's an odd number of teams
//                         if ($request->sports['num_of_teams'] % 2 == 1 && $temp == $request->sports['num_of_teams']) {
//                             // Bypass the last team to the next round
//                             $schedule = Schedule::where('id', $tempNextMatchID[$index])->first();
//                             $newData['first_team_id'] = $data;
//                             $newData['second_team_id'] = null; // No second team
//                             $newData['match_no'] = $schedule->match_no;
//                             $newData['match_round'] = $schedule->match_round;
//                             $newData['next_match_id'] = $schedule->next_match_id;
//                             $schedule->update($newData);
//                             return json_encode($schedule);
//                         } else {
//                             // Pair the teams normally
//                             if ($countInTwos == 1) {
//                                 $newData['first_team_id'] = $data;
//                             } else {
//                                 $newData['second_team_id'] = $data;
//                                 $newData['match_no'] = $numOfMatch;
//                                 $newData['match_round'] = "Round " . $numberOfRounds;
//                                 $newData['next_match_id'] = $tempNextMatchID[$index];
//                                 $numOfMatch += 1;
//                                 Schedule::create($newData);  // Save match pairing
//                                 $newData = $template; // Reset to template
//                                 $anotherCounter++;
//                                 if ($anotherCounter == 2) {
//                                     $anotherCounter = 0;
//                                     $index++;
//                                 }
//                             }
//                         }
//                         $temp++;
//                     }
//                     return json_encode($teams);
//                 } else {
//                     if($counter == 1){
//                         $newData['match_round'] = "Round " . $numberOfRounds;
//                         $newData['match_no'] = $x;
//                         $schedule = Schedule::create($newData);
//                         $tempNextMatchID[] = $schedule->id;
//                         $numberOfRounds--;
//                     } else if($counter > 1 && $counter < 4){
//                         $newData['match_no'] = $x;
//                         $newData['match_round'] = "Round " . $numberOfRounds;
//                         $newData['next_match_id'] = $tempNextMatchID[$index];
//                         $schedule = Schedule::create($newData);
//                         $tempNextMatchID[] = $schedule->id;
//                         $hold = 0;

//                     } else{
//                         if($counter == 4){
//                             $numberOfRounds--;
//                         }
//                         $newData['match_no'] = $x;
//                         $newData['match_round'] = "Round " . $numberOfRounds;
//                         $newData['next_match_id'] = $tempNextMatchID[$index];
//                         $schedule = Schedule::create($newData);
//                         $tempNextMatchID[] = $schedule->id;

//                         $hold = 0;
//                         if($counter == 7 ){
//                             $hold = $tempNextMatchID[3];
//                             $tempNextMatchID[3] = $tempNextMatchID[6];
//                             $tempNextMatchID[6] = $hold;

//                             $hold = $tempNextMatchID[4];
//                             $tempNextMatchID[4] = $tempNextMatchID[5];
//                             $tempNextMatchID[5] = $hold;
//                             $numberOfRounds--;
//                             // return json_encode($tempNextMatchID);
//                         }
//                     }
//                 }
//                 if ($countInTwosMain == 1 && $counter != 1) {
//                     $index++;
//                 }

//                 $newData = $template; // Reset to template
//                 $counter++;
//             }
    
//             return json_encode($result);
    
//         } catch (Exception $e) {
//             return json_encode(['error' => $e->getMessage()]);
//         }
//     }

//     public function scheduledMatch($sportId){
//         try{

//             $records = Schedule::whereNotNull(['date','time'], 'and' )
//                             ->whereNull(['winner_team_id'])
//                             ->where('sports_id', $sportId)
//                             ->get();
//             $records->load(['firstTeam', 'secondTeam', 'winningTeam', 'sports', 'gameStatus']);

//             return ScheduleResource::collection($records);

//         } catch (Exception $e) {
//             return json_encode(['error' => $e->getMessage()]);
//         }
//     }
//     private function calculateRounds($numberOfTeams) {
//         if ($numberOfTeams < 2) {
//             return 0; // No rounds needed if fewer than 2 teams
//         }
//         // Calculate the number of rounds needed
//         return ceil(log($numberOfTeams, 2));
//     }
// }
