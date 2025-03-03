<?php

namespace App\Http\Requests\Store;

use App\Models\Library\LibGender;
use App\Models\Library\LibPosition;
use App\Models\Sports\Team;
use Illuminate\Foundation\Http\FormRequest;

class PlayerStoreRequest extends FormRequest
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
            'player.student_no' => 'required|numeric',
            'player.age' => 'required|integer',
            'player.scua' => 'required',
            'player.parent_sign' => 'required',
            'player.image' => 'required',
            'player.team_id' => 'sometimes|integer|exists:teams,id',
            'player.position_id' => 'sometimes|integer|exists:lib_positions,id',
            'player.basic_information_id' => 'sometimes|integer',
            'player.lib_player_status_id' => 'required|integer',

            'player.cor' => 'required|mimes:pdf',  // Accepts only PDF files, with a max size of 2MB
            'player.med_cert' => 'required|mimes:pdf',
            'player.psa' => 'required|mimes:pdf',

            'info.first_name' => 'required|string',
            'info.middle_name' => 'required|string',
            'info.last_name' => 'required|string',
            'info.phone_no' => 'required|numeric',
            'info.lib_gender_id' => 'required|integer|exists:lib_genders,id',
        ];
    }
}
