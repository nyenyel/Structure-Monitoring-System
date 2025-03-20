<?php

namespace App\Http\Requests\Store;

use App\Models\Library\LibAward;
use App\Models\Library\LibGender;
use App\Models\Sports\College;
use App\Models\Sports\Sports;
use Illuminate\Foundation\Http\FormRequest;

class TeamStoreRequest extends FormRequest
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
            'team.title' => 'required|string',
            'team.sports_id' => 'required|integer|exists:sports,id',
            'team.college_id' => 'required|integer|exists:colleges,id',
            'team.lib_award_id' => 'sometimes|integer|exists:lib_awards,id',
            'team.coach_id' => 'sometimes|integer',
            'team.logo' => 'required',
            'info.first_name' => 'required|string',
            'info.middle_name' => 'required|string',
            'info.last_name' => 'required|string',
            'info.phone_no' => 'required|numeric',
            'info.lib_gender_id' => 'required|integer|exists:lib_genders,id',
            'sec_info.first_name' => 'required|string',
            'sec_info.middle_name' => 'required|string',
            'sec_info.last_name' => 'required|string',
            'sec_info.phone_no' => 'required|numeric',
            'sec_info.lib_gender_id' => 'required|integer|exists:lib_genders,id',
        ];
    }

    public function messages()
{
    return [
        'team.title.required' => 'The team title is required.',
        'team.sports_id.required' => 'Please Select a sports the team will play.',
        'team.sports_id.exists' => 'The selected sports is invalid.',
        'team.college_id.required' => 'Please Select a college they belong to.',
        'team.college_id.exists' => 'The selected college ID is invalid.',
        'team.logo.required' => 'The team logo is required.',
        'info.first_name.required' => 'The first name is required.',
        'info.middle_name.required' => 'The middle name is required.',
        'info.last_name.required' => 'The last name is required.',
        'info.phone_no.required' => 'The phone number is required.',
        'info.phone_no.numeric' => 'The phone number must be a number.',
        'info.lib_gender_id.required' => 'Please select gender.',
        'info.lib_gender_id.exists' => 'The selected gender ID is invalid.',
    ];
}
}
