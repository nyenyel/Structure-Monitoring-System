<?php

namespace App\Http\Requests\Update;

use App\Http\Requests\Store\CollegeStoreRequest;
use Illuminate\Foundation\Http\FormRequest;

class CollegeUpdateRequest extends FormRequest
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
            //
            'title'=>'required|string',
            'acronym' => 'required|string',
            'logo' => 'sometimes',
        ];
    }
}