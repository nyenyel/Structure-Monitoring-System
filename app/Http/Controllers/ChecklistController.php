<?php

namespace App\Http\Controllers;

use App\Models\Checklist;
use App\Models\Others\Banner;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class ChecklistController extends Controller
{
    public function addToChecklist(Request $request){
        $validated = $request->validate([
            'sport_id' => 'required',
            'college_id' => 'required',
        ]);

        $banner = Banner::where('is_default', true)->value('id');
        $data = [
            'sport_id' => $validated['sport_id'],
            'college_id' => $validated['college_id'],
            'banner_id' => $banner,
        ];

        $new = Checklist::create($data);
        return response()->json($new);
    }

    public function getChecklist() {
        $banner = Banner::where('is_default', true)->value('id');
        $user = Auth::user();
        $college = $user->college->value('id');

        $checklist = Checklist::where('banner_id', $banner)->where('college_id', $college)->get(); 
        return response()->json($checklist);
    }

    public function removeChecklist(Checklist $checklist){
        $checklist->delete();
        return response()->json(['message' => 'Data Deleted']);
        
    }
}
