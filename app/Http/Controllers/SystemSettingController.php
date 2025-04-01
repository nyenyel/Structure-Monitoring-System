<?php

namespace App\Http\Controllers;

use App\Models\SystemSetting;
use App\Models\User;
use App\Services\SemaphoreService;
use Illuminate\Http\Request;

class SystemSettingController extends Controller
{

    /**
     * Display the specified resource.
     */
    public function show(SystemSetting $system)
    {
        return response()->json($system);
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, SystemSetting $system)
    {
        $semaphore = new SemaphoreService();
        $valdated = $request->validate([
            'irr' => 'string',
            'deadline' => 'date',
        ]);
        $user = User::with('basicInformation')
                    ->get()
                    ->pluck('basicInformation.phone_no')
                    ->toArray();

        $validNumbers = array_values(array_filter($user, function($num) {
            return preg_match('/^09\d{9}$/', $num);
        }));
        $new = $system->update($valdated);

        $irr = "The updated IRR is: " . $valdated['irr'];
        $deadline = "The updated Deadline is: " . $valdated['deadline'];

        $message = $irr . " ----------- " . $deadline;
        $response = $semaphore->bulkSMS($validNumbers, $message);

        return response()->json([$validNumbers, $message, $response]);
    }

}
