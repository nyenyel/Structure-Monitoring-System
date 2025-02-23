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
        $valdated = $request->validate([
            'irr' => 'string',
            'deadline' => 'date',
        ]);
        $new = $system->update($valdated);
        return response()->json($new);
    }

}
