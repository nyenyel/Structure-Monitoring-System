<?php

namespace App\Http\Controllers;

use App\Models\SystemSetting;
use Illuminate\Http\Request;

class SystemSettingController extends Controller
{

    /**
     * Display the specified resource.
     */
    public function show(SystemSetting $system)
    {
        return response()->json($system);
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, SystemSetting $system)
    {
        $new = $system->update($request->validated());
        return response()->json($new);
    }

}
