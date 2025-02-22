<?php

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class TeamResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @return array<string, mixed>
     */
    public function toArray(Request $request): array
    {
        return [
            'id'=> $this->id,
            'title'=> $this->title,
            'logo'=> $this->logo,
            'age'=> $this->age,
            'scua'=> $this->scua,
            'sports_id'=> new SportsResource($this->whenLoaded('sports')),
            'college_id'=> new CollegeResource($this->whenLoaded('college')),
            'lib_award_id'=>new LibraryResource($this->whenLoaded('award')),
            'coach_id'=> new BasicInformationResource($this->whenLoaded('coach')),
            'players'=> PlayerResource::collection($this->whenLoaded('player'))
        ];
    }
}
