<?php

namespace App\Http\Controllers\API\v1\BasicControllers;

use App\Http\Controllers\Controller;
use App\Http\Requests\Store\PoistionStoreRequest;
use App\Http\Resources\PositionResource;
use App\Models\Library\LibPosition;
use Illuminate\Http\Request;

class LibPositionController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $position = LibPosition::with(['sports'])->get();
        return PositionResource::collection($position);
    }


    /**
     * Store a newly created resource in storage.
     */
    public function store(PoistionStoreRequest $request)
    {
        $position= LibPosition::create($request->validated());
        $position->load(['sports']);
        return PositionResource::make($position);
    }

    /**
     * Display the specified resource.
     */
    public function show(LibPosition $position)
    {
        $position->load(['sports']);
        return PositionResource::make($position);

    }


    /**
     * Update the specified resource in storage.
     */
    public function update(PoistionStoreRequest $request, LibPosition $position)
    {
        $position->update($request->validated());
        $position->load(['sports']);
        return PositionResource::make($position);
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(LibPosition $position)
    {
        $position->delete();
        return response()->noContent();
    }
}
