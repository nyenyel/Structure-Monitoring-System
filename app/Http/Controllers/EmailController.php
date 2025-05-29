<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Mail;
use Illuminate\Support\Facades\Log;


class EmailController extends Controller
{
    public function sendAlarmEmail(Request $request)
    {
        // Validate input
        Log::info('Received email send request', $request->all());

        // Step 1: Validate input
        try {
            $data = $request->validate([
                'email' => 'required|email',
                'title' => 'required|string|max:255',
            ]);
            Log::info('Validation passed', $data);
        } catch (\Exception $e) {
            Log::error('Validation failed', ['error' => $e->getMessage()]);
            return response()->json(['error' => 'Validation failed', 'details' => $e->getMessage()], 422);
        }

        $toEmail = $data['email'];
        $title = $data['title'];

        // Step 2: Attempt to send email
        try {
            Log::info("Attempting to send email to {$toEmail}...");

            Mail::raw("You have a scheduled alarm on {$title}", function ($message) use ($toEmail) {
                $message->to($toEmail)
                        ->subject("You have been tagged in the tasklogs App");
            });

            Log::info("Email send attempt completed to {$toEmail}");

            return response()->json(['message' => 'Email sent successfully']);
        } catch (\Exception $e) {
            Log::error("Failed to send email", ['error' => $e->getMessage()]);
            return response()->json(['error' => 'Failed to send email', 'details' => $e->getMessage()], 500);
        }
    }
}
