<?php

namespace App\Http\Controllers\api\v1\BasicController;

use App\Http\Controllers\Controller;
use App\Http\Resources\UserRegistrationResource;
use App\Models\Library\LibGender;
use App\Models\Library\LibRole;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;

class UserController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $user = Auth::user();
        
        if ($user->college == null){
            $data = User::all();
            $data->load(['basicInformation', 'role', 'college', 'team']);
        } else {
            $data = User::where('college_id', $user->college->id)->get();
            $data->load(['basicInformation', 'role', 'college', 'team']);
        }
        
        return UserRegistrationResource::collection($data);/*  */
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        //
    }

    /**
     * Display the specified resource.
     */
    public function show(User $user)
    {
        $user->load(['role', 'basicInformation.gender', 'college', 'team']);
        return UserRegistrationResource::make($user);
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, User $user)
    {
        $maxGender = LibGender::count();
        $maxRole = LibRole::count();

        $validated = $request->validate([
            'user.email' => 'required|email|',
            'user.basic_information_id' => 'sometimes|integer',
            'user.lib_role_id' => 'sometimes|integer|min:1|max:' . $maxRole,
            'user.password' => 'sometimes',
            'info.first_name' => 'required|string',
            'info.middle_name' => 'required|string',
            'info.last_name' => 'required|string',
            'info.phone_no' => 'required|numeric',
            'info.lib_gender_id' => 'required|integer|min:1|max:' . $maxGender,
        ]);


        $userData = $user->update($validated['user']);
        $basicInformation = $user->basicInformation->update($validated['info']);

        return json_encode([
            'received request' => $request,
            'user' => $userData,
            'basic information' => $basicInformation
        ]);
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(User $user)
    {
        $temp =$user->basicInformation;
        $user->delete();
        $temp->delete();
        return json_encode(['message' => 'Data Deleted']);
    }
}
