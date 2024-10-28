<?php

namespace App\Http\Requests\Store;

use App\Models\Library\LibIncidentStatus;
use App\Models\User;
use Illuminate\Foundation\Http\FormRequest;

class IncidentStoreRequest extends FormRequest
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
        $maxUser = User::count();
        $maxStatus = LibIncidentStatus::count();
        return [
            'title' => 'required|string',
            'desc' => 'required|string',
            'lib_incident_status_id' => 'required|integer|min:1|max:'.$maxStatus,
            'reported_by_id' => 'required|integer|min:1|max:'. $maxUser
        ];
    }
}
