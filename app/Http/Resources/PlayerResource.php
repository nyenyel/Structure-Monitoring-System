<?php

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class PlayerResource extends JsonResource
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
            'student_no' => $this->student_no,
            'cor' => $this->cor,
            'med_cert' => $this->med_cert,
            'psa' => $this->psa,
            'age'=> $this->age,
            'scua'=> $this->scua,
            'image' => $this->image,
            'parent_sign' => $this->parent_sign,
            'team_id'=> new TeamResource($this->whenLoaded('team')),
            'position_id' => new PositionResource($this->whenLoaded('position')),
            'basic_information_id' => new BasicInformationResource($this->whenLoaded('basicInformation')),
            'gender' => new LibraryResource($this->whenLoaded('gender')),
            'status' => new LibraryResource($this->whenLoaded('status'))
        ];
    }
}
