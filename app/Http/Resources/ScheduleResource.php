<?php

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class ScheduleResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @return array<string, mixed>
     */
    public function toArray(Request $request): array
    {
        return [
            'id' => $this->id,
            'date' => $this->date,
            'time' => $this->time,
            'match_no' => $this->match_no,
            'match_round' => $this->match_round,
            'next_match_id' => $this->next_match_id,
            'referee_full_name' => $this->referee_full_name,
            'sports' => $this->whenLoaded('sports', function () {
            return new SportsResource($this->sports);
            }),
            'status' => $this->whenLoaded('gameStatus', function () {
                return new LibraryResource($this->gameStatus);
            }),
            'firstTeam' => $this->whenLoaded('firstTeam', function () {
                return new TeamResource(resource: $this->firstTeam);
            }),
            'secondTeam' => $this->whenLoaded('secondTeam', function () {
                return new TeamResource($this->secondTeam);
            }),
            'winningTeam' => $this->whenLoaded('winningTeam', function () {
                return new TeamResource($this->winningTeam);
            }), 
        ];
    }
}
