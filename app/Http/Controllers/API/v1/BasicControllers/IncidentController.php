<?php

namespace App\Http\Controllers\API\v1\BasicControllers;

use App\Http\Controllers\Controller;
use App\Http\Requests\Store\IncidentStoreRequest;
use App\Http\Requests\Update\IncidentUpdateRequest;
use App\Http\Resources\IncidentResource;
use App\Models\Sports\Incident;
use Illuminate\Http\Request;

class IncidentController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $incident = Incident::with(['reportedBy.basicInformation.gender', 'incidentStatus'])->get();
        return IncidentResource::collection($incident);
    }



    /**
     * Store a newly created resource in storage.
     */
    public function store(IncidentStoreRequest $request)
    {
        $incident = Incident::create($request->validated());
        $incident->load(['reportedBy.basicInformation.gender', 'incidentStatus']);
        return IncidentResource::make($incident);
    }

    /**
     * Display the specified resource.
     */
    public function show(Incident $incident)
    {
        $incident->load(['reportedBy.basicInformation.gender', 'incidentStatus']);
        return IncidentResource::make($incident);

    }


    /**
     * Update the specified resource in storage.
     */
    public function update(IncidentUpdateRequest $request, Incident $incident)
    {
        // return IncidentResource::make($request);

        $incident->update($request->validated());
        $incident->load([ 'incidentStatus']);
        return IncidentResource::make($incident);
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(Incident $incident)
    {
        $incident->delete();
    return response()->noContent();
    }
}
