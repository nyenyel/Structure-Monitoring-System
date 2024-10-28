<?php

use App\Http\Controllers\API\v1\AuthController;
use App\Http\Controllers\api\v1\BasicController\BannerController;
use App\Http\Controllers\API\v1\BasicControllers\CollegeController;
use App\Http\Controllers\API\v1\BasicControllers\IncidentController;
use App\Http\Controllers\API\v1\BasicControllers\LibPositionController;
use App\Http\Controllers\API\v1\BasicControllers\PlayerController;
use App\Http\Controllers\API\v1\BasicControllers\ScheduleController;
use App\Http\Controllers\API\v1\BasicControllers\SportsController;
use App\Http\Controllers\API\v1\BasicControllers\TeamController;
use App\Http\Controllers\api\v1\BatchScheduleController;
use App\Http\Controllers\API\v1\TallyController;
use App\Http\Controllers\API\v1\BasicController\UserController;
use App\Http\Controllers\api\v1\SMSController;
use App\Http\Resources\UserRegistrationResource;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

Route::prefix('v1')->group( function () {
    Route::prefix('basic-controller')->group( function (){
        Route::apiResource('college', CollegeController::class);
        Route::apiResource('sports', SportsController::class);
        Route::apiResource('team', TeamController::class);
        Route::apiResource('position', LibPositionController::class);
        Route::apiResource('player', PlayerController::class);
        Route::apiResource('incident', IncidentController::class);
        Route::apiResource('schedule', ScheduleController::class);
        Route::apiResource('banner', BannerController::class);
        Route::apiResource('user', UserController::class);
        Route::put('approve-player', [PlayerController::class, 'approvePlayer'])->name('approvePlayer');
    });
    Route::prefix('compute')->group(function(){
        Route::get('tally', [TallyController::class, 'tally']);
    });
    Route::post('batch-schedule', [BatchScheduleController::class, 'batchSchedule']);
    Route::get('scheduled-match/{sportsId}', [BatchScheduleController::class, 'scheduledMatch']);
    Route::post('single-sms', [SMSController::class, 'sendMessage'])->name('sendMessage');
});

Route::prefix('auth')->group(function (){
    Route::post('register', [AuthController::class, 'register']);
    Route::get('user', [AuthController::class, 'user']);
    Route::post('login', [AuthController::class, 'login'])->name('login');
    Route::post('logout', [AuthController::class, 'logout'])->middleware('auth:sanctum');
});

Route::get('/user', function (Request $request) {
    $data = $request->user();
    $data->load('basicInformation.gender', 'role' );
    return UserRegistrationResource::make($data);
})->middleware('auth:sanctum');
