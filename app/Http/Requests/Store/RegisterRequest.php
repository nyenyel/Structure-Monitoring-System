<?php

namespace App\Http\Requests\Store;

use Illuminate\Foundation\Http\FormRequest;

class RegisterRequest extends FormRequest
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
        return [
                'user.email' => 'required|email|unique:users,email',
                'user.basic_information_id' => 'sometimes|integer',
                'user.lib_role_id' => 'sometimes|integer|exists:lib_roles,id',
                'user.password' => 'required|min:8|max:16',
                'user.college_id' => 'nullable',
                'info.first_name' => 'required|string',
                'info.middle_name' => 'required|string',
                'info.last_name' => 'required|string',
                'info.phone_no' => 'required|numeric',
                'info.lib_gender_id' => 'required|integer|exists:lib_genders,id',
        ];
    }

    public function messages()
    {
        return [
            'user.email.required' => 'The email address is required.',
            'user.email.email' => 'Please provide a valid email address.',
            'user.email.unique' => 'This email address is already registered.',
            'user.basic_information_id.integer' => 'The basic information ID must be an integer.',
            'user.lib_role_id.integer' => 'Please select a role.',
            'user.lib_role_id.exists' => 'The selected role ID does not exist.',
            'user.password.required' => 'The password is required.',
            'user.password.min' => 'The password must be at least 8 characters long.',
            'user.password.max' => 'The password must not exceed 16 characters.',
            'info.first_name.required' => 'The first name is required.',
            'info.middle_name.required' => 'The middle name is required.',
            'info.last_name.required' => 'The last name is required.',
            'info.phone_no.required' => 'The phone number is required.',
            'info.phone_no.numeric' => 'The phone number must be a valid number.',
            'info.lib_gender_id.required' => 'Please select a gender.',
            'info.lib_gender_id.exists' => 'The selected gender ID is invalid.',
        ];
    }
}
