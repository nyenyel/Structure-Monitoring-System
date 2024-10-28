<?php

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class IncidentResource extends JsonResource
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
            'title' => $this->title,
            'desc' => $this->desc,
            'lib_incident_status_id' => new LibraryResource($this->whenLoaded('incidentStatus')),
            'reported_by_id' => new UserRegistrationResource($this->whenLoaded('reportedBy'))
        ];
    }
}
