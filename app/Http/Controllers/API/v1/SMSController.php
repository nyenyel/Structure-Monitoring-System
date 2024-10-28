<?php

namespace App\Http\Controllers\api\v1;

use App\Http\Controllers\Controller;
use App\Http\Requests\Store\SMSSendRequest;
use App\Services\SemaphoreService;
use Illuminate\Http\Request;

class SMSController extends Controller
{
    protected $semaphore;

    public function __construct(SemaphoreService $semaphore)
    {
        $this->semaphore = $semaphore;
    }

    public function sendMessage(SMSSendRequest $request){
        $validated = $request->validated();

        $response = $this->semaphore->sendSMS($validated['number'], $validated['message']);
        // return response()->json($response->json(), $response->status());
        return response()->json(['data'=>$response]);

    }

    public function sendBulkMessage(Request $request){
        $request->validate([
            'numbers' => 'required|array|min:1',  // Expecting an array of phone numbers
            'numbers.*' => 'required|string',     // Ensure each number is a string
            'message' => 'required|string|max:160', // Limit message to 160 characters
        ]);

        $response = $this->semaphore->bulkSMS($request->numbers, $request->message);

        return response()->json($response);
    }
}
