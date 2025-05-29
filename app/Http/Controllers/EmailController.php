<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Mail;

class EmailController extends Controller
{
    public function sendAlarmEmail(Request $request)
    {
        // Validate input
        $data = $request->validate([
            'email' => 'required|email',
            'title' => 'required|string|max:255',
        ]);

        $toEmail = $data['email'];
        $title = $data['title'];

        // Send email inline
        try {
            Mail::raw("You have a scheduled alarm on {$title}", function ($message) use ($toEmail, $title) {
                $message->to($toEmail)
                        ->subject("You have been tagged in the tasklogs App");
            });

            return response()->json(['message' => 'Email sent successfully']);
        } catch (\Exception $e) {
            return response()->json(['error' => 'Failed to send email', 'details' => $e->getMessage()], 500);
        }
    }
}
