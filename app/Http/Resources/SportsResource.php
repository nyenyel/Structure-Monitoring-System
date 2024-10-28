<?php

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class SportsResource extends JsonResource
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
            'title' => $this->title,
            'venue' => $this->venue,
            'logo'=> $this->logo,
            'position' => PositionResource::collection($this->whenLoaded('position')),
            'team' => TeamResource::collection($this->whenLoaded('team')),
            'schedule' => ScheduleResource::collection($this->whenLoaded('schedule'))

        ];
    }
}
