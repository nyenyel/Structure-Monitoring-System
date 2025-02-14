<?php

namespace App\Http\Controllers\API\v1\BasicControllers;

use App\Http\Controllers\Controller;
use App\Http\Requests\Store\PlayerStoreRequest;
use App\Http\Requests\Update\PlayerUpdateRequest;
use App\Http\Resources\PlayerResource;
use App\Models\Users\BasicInformation;
use App\Models\Users\Player;
use Exception;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Storage;
use League\CommonMark\Exception\IOException;

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
        $validated =$request->validated();
        // Store files and get their URLs
        if ($request->hasFile('player.cor')) {
            try {
                $corFileName = 'cor_' . time() . '.pdf'; // Custom file name
                $corPath = $request->file('player.cor')->storeAs('cor', $corFileName, 'public'); // Store in 'storage/app/public/cor'
                Log::debug("COR file stored at: {$corPath}");
                $validated['player']['cor'] = url("storage/cor/{$corFileName}"); // Generate custom URL
            } catch (\Exception $e) {
                Log::error('Error storing COR file: ' . $e->getMessage());
            }
        }
        
        if ($request->hasFile('player.med_cert')) {
            try {
                $medCertFileName = 'med_cert_' . time() . '.pdf'; // Custom file name
                $medCertPath = $request->file('player.med_cert')->storeAs('med_cert', $medCertFileName, 'public'); // Store in 'storage/app/public/med_cert'
                Log::debug("Medical Certificate file stored at: {$medCertPath}");
                $validated['player']['med_cert'] = url("storage/med_cert/{$medCertFileName}"); // Generate custom URL
            } catch (\Exception $e) {
                Log::error('Error storing Medical Certificate file: ' . $e->getMessage());
            }
        }
        
        if ($request->hasFile('player.psa')) {
            try {
                $psaFileName = 'psa_' . time() . '.pdf'; // Custom file name
                $psaPath = $request->file('player.psa')->storeAs('psa', $psaFileName, 'public'); // Store in 'storage/app/public/psa'
                Log::debug("PSA file stored at: {$psaPath}");
                $validated['player']['psa'] = url("storage/psa/{$psaFileName}"); // Generate custom URL
            } catch (\Exception $e) {
                Log::error('Error storing PSA file: ' . $e->getMessage());
            }
        }
        
        // return $validated;
        try{

            $basicInformation = BasicInformation::create($validated['info']);
            $validated['player']['basic_information_id'] = $basicInformation->id;
            $validated['player']['scua'] = $validated['player']['scua'] === "true" ? true:false;
            
            $player = Player::create($validated['player']);
            $player->load($this->relationship);
            return new PlayerResource($player);
        
        } catch (IOException $e){
            Log::error("error: ". $e);
        }
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
