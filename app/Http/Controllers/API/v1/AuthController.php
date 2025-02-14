<?php

namespace App\Http\Controllers\API\v1;

use App\Http\Controllers\Controller;
use App\Http\Requests\Store\RegisterRequest;
use App\Http\Resources\UserRegistrationResource;
use App\Models\Library\LibGender;
use App\Models\Library\LibRole;
use App\Models\User;
use App\Models\Users\BasicInformation;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Log;

class AuthController extends Controller
{
    public function register(RegisterRequest $request)
    {
        try {
            $maxGender = LibGender::count();
            $maxRole = LibRole::count();

            $validated = $request->validated();

            Log::info('Validation successful', $validated);

            $basicInformation = BasicInformation::create($validated['info']);
            $validated['user']['basic_information_id'] = $basicInformation->id;

            $validated['user']['college_id'] = !in_array($validated['user']['lib_role_id'], [4, 5]) !== 4 ? null : $validated['user']['college_id'];

            $user = User::create($validated['user']);
            $token = $user->createToken('auth_token')->plainTextToken;

            Log::info('User registration successful', ['user' => $user]);
            return response()->json([
                'user' => $user,
                'token' => $token
            ]);

        } catch (\Illuminate\Validation\ValidationException $e) {
            Log::error('Validation Error: ' . $e->getMessage(), [
                'errors' => $e->errors()
            ]);
            return response()->json([
                'error' => 'Validation failed. Please check your input.',
                'messages' => $e->errors()
            ], 422);
        } catch (\Exception $e) {
            Log::error('Registration Error: ' . $e->getMessage());
            return response()->json(['error' => 'Registration failed. Please try again.'], 500);
        }
    }

    public function login(Request $request)
    {
        try {
            $validated = $request->validate([
                'email' => 'required|email|exists:users,email',
                'password' => 'required',
            ]);

            Log::info('Validation successful', $validated);

            $user = User::where('email', $validated['email'])->first();
            $user->load('basicInformation' ,'role');

            if (!$user || !Hash::check($validated['password'], $user->password)) {
                Log::warning('Invalid Credentials', ['email' => $validated['email']]);
                return response()->json(['message' => 'Invalid Credentials!'], 401);
            }

            $token = $user->createToken('auth_token')->plainTextToken;

            Log::info('User login successful', ['user' => $user]);

            return response()->json([
                'user' => UserRegistrationResource::make($user),
                'token' => $token
            ]);
        } catch (\Illuminate\Validation\ValidationException $e) {
            Log::error('Validation Error: ' . $e->getMessage(), [
                'errors' => $e->errors()
            ]);
            return response()->json([
                'error' => 'Validation failed. Please check your input.',
                'messages' => $e->errors()
            ], 422);
        } catch (\Exception $e) {
            Log::error('Login Error: ' . $e->getMessage());
            return response()->json(['error' => 'Login failed. Please try again.'], 500);
        }
    }

    public function user()
    {
        $user = Auth::user();
        
        if (!$user) {
            return response()->json(['error' => 'User not authenticated'], 401);
        }

        return response()->json(['user' => $user]);
    }

    public function logout(Request $request){
        $request->user()->tokens()->delete();

        return response()->json([
            'message' => 'You are logged out'
        ]);
    }
}
