<?php

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class UserRegistrationResource extends JsonResource
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
            'email' => $this->email,
            // 'token' => $this->token,
            'role' => new LibraryResource($this->whenLoaded('role')),
            'basic_information' => new BasicInformationResource($this->whenLoaded('basicInformation')),
            'college' => new CollegeResource($this->whenLoaded('college')),
            'incident'=> IncidentResource::collection($this->whenLoaded('incident')),
        ];
    }
}
