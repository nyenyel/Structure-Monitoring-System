<?php

namespace App\Http\Requests\Store;

use App\Models\Library\LibGameStatus;
use App\Models\Sports\Sports;
use App\Models\Sports\Team;
use Illuminate\Foundation\Http\FormRequest;

class ScheduleStoreRequest extends FormRequest
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
            'date'=> 'sometimes|date|nullable',
            'time' => 'sometimes|nullable',
            'sports_id' => 'required|integer|exists:sports,id',
            'is_pointing_system' => 'sometimes',
            'lib_game_statuses_id' => 'required|integer|exists:lib_game_statuses,id',
            'first_team_id' => 'sometimes|integer|nullable',
            'second_team_id'=> 'sometimes|integer|nullable',
            'first_team_score' => 'sometimes|integer|nullable',
            'second_team_score'=> 'sometimes|integer|nullable',
            'winner_team_id'=> 'sometimes|integer|nullable',
            'referee_full_name'=> 'sometimes|string',
            'reason'=> 'sometimes|string',
        ];
    }
}
