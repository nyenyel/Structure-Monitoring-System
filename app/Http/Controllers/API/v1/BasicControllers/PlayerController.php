<?php

namespace App\Http\Controllers\API\v1\BasicControllers;

use App\Http\Controllers\Controller;
use App\Http\Requests\Store\PlayerStoreRequest;
use App\Http\Requests\Update\PlayerUpdateRequest;
use App\Http\Resources\PlayerResource;
use App\Models\Users\BasicInformation;
use App\Models\Users\Player;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Storage;

class PlayerController extends Controller
{

    protected $relationship = [
        'team',
        'position',
        'basicInformation.gender',
        'status'
    ];
    /**;
     * Display a listing of the resource.
     */
    public function index()
    {
        $player = Player::with($this->relationship)->get();
        return PlayerResource::collection($player);
    }


    /**
     * Store a newly created resource in storage.
     */
    public function store(PlayerStoreRequest $request)
    {
        
        // $data = $request;
        // $data['player']['lib_player_status_id'] = 1;

        $validated = $request->validated();
        // Store files and get their URLs
        if ($request->hasFile('player.cor')) {
            $corPath = $request->file('player.cor')->store('cor', 'public');  // Store in 'storage/app/public/cor'
            $validated['player']['cor'] = Storage::url($corPath);  // Save the URL to the database
        }

        if ($request->hasFile('player.med_cert')) {
            $medCertPath = $request->file('player.med_cert')->store('med_cert', 'public');  // Store in 'storage/app/public/med_cert'
            $validated['player']['med_cert'] = Storage::url($medCertPath);  // Save the URL to the database
        }

        if ($request->hasFile('player.psa')) {
            $psaPath = $request->file('player.psa')->store('psa', 'public');  // Store in 'storage/app/public/psa'
            $validated['player']['psa'] = Storage::url($psaPath);  // Save the URL to the database
        }
        $basicInformation = BasicInformation::create($validated['info']);
        $validated['player']['basic_information_id'] = $basicInformation->id;
        return $validated;
        
        $player = Player::create($validated['player']);
        $player->load($this->relationship);
        return new PlayerResource($player);
    }

    /**
     * Display the specified resource.
     */
    public function show(Player $player)
    {
        $player->load($this->relationship);
        return PlayerResource::make($player);
    }


    /**
     * Update the specified resource in storage.
     */
    public function update(PlayerUpdateRequest $request, Player $player)
    {
        $validated = $request->validated();
        $player->update($request['player']);
        $player->basicInformation->update($validated['info']);
        $player->load($this->relationship);
        return PlayerResource::make($player);
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy( Player $player)
    {
        $player->delete();
        $player->basicInformation->delete();
        return response()->noContent();
    }

    public function approvePlayer(PlayerUpdateRequest $request, Player $player){
        $player->update($request->validated());
        return json_encode(['message' => 'Player Accepted']);
    }
}
