<?php

namespace App\Services;

use Illuminate\Container\Attributes\Log;
use Illuminate\Support\Facades\Http;
use Illuminate\Support\Facades\Log as FacadesLog;

class SemaphoreService
{
    protected $apiKey;
    protected $senderName;

    public function __construct()
    {
        $this->apiKey = config('services.semaphore.api_key');
        $this->senderName = config('services.semaphore.sender_name');
    }

    public function sendSMS($number, $message)
    {
        $url = 'https://api.semaphore.co/api/v4/messages';

        $data = [
            'apikey' => $this->apiKey,
            'number' => $number,
            'message' => $message,
            'sendername' => $this->senderName,
        ];

        $ch = curl_init($url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $data);

        $response = curl_exec($ch);

        if (curl_errno($ch)) {
            return ['error' => curl_error($ch)];
        }

        curl_close($ch);
        return response()->json([
            'response' => json_decode($response),
            'input' => $data
        ]);
    }

    public function bulkSMS(array $numbers, $message)
    {
        $url = 'https://api.semaphore.co/api/v4/messages';

        $data = [
            'apikey' => $this->apiKey,
            'number' => implode(',', $numbers),
            'message' => $message,
            'sendername' => $this->senderName,
        ];

        $ch = curl_init($url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $data);

        $response = curl_exec($ch);
        if (curl_errno($ch)) {
            return ['error' => curl_error($ch)];
        }

        curl_close($ch);
        return json_decode($response, true);
    }
}