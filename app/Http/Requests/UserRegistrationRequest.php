<?php

namespace App\Http\Requests;

use App\Models\Library\LibGender;
use App\Models\Library\LibRole;
use Illuminate\Foundation\Http\FormRequest;

class UserRegistrationRequest extends FormRequest
{
    /**
     * Determine if the user is authorized to make this request.
     */
    public function authorize(): bool
    {
        return true;
    }

    /**
     * Get the validation rules that apply to the request.
     *
     * @return array<string, \Illuminate\Contracts\Validation\ValidationRule|array<mixed>|string>
     */
    public function rules(): array
    {
        $maxGender = LibGender::count();
        $maxRole = LibRole::count();
        return [
            'user.email' => 'required|email|unique:users,email',
            'user.basic_information_id' => 'sometimes|integer',
            'user.lib_role_id' => 'sometimes|integer|min:1|max:' . $maxRole,
            'user.password' => 'required|min:8|max:16',
            'info.first_name' => 'required|string',
            'info.middle_name' => 'sometimes|string',
            'info.last_name' => 'required|string',
            'info.phone_no' => 'required|numeric',
            'info.lib_gender_id' => 'required|integer|min:1|max:' . $maxGender,
        ];
    }
}
