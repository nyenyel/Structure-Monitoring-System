<?php

namespace App\Http\Controllers\API\v1\BasicControllers;

use App\Http\Controllers\Controller;
use App\Http\Requests\Store\CollegeStoreRequest;
use App\Http\Requests\Update\CollegeUpdateRequest;
use App\Http\Resources\CollegeResource;
use App\Models\Sports\College;
use Error;
use Exception;
use Illuminate\Console\View\Components\Task;
use Illuminate\Http\Request;
use Illuminate\Support\Str;

use Illuminate\Routing\Controllers\HasMiddleware;
use Illuminate\Routing\Controllers\Middleware;

class CollegeController extends Controller 
// implements HasMiddleware
{

    // public static function middleware()
    // {
    //     return [
    //         new Middleware('auth:sanctum', except: ['index' ,'show']) 
    //     ];
    // }
    
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $data = College::where('delete', false)->get();
        return CollegeResource::collection($data);
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(CollegeStoreRequest $request)
    {
        $validated = $request->validated();

        $img = $request->file('logo'); // Correct way to access the file in an array

        // Get the file's extension
        $ext = $img->getClientOriginalExtension();

        // Generate a random filename
        $imageName = Str::random(20) . '.' . $ext;

        // Move the file to the 'image' directory
        $img->move(public_path('image'), $imageName);

        // Store the URL or path in the database
        $validated['logo'] = asset('image/' . $imageName);

        $college = College::create($validated);
        return CollegeResource::make($college);
        
    }

    /**
     * Display the specified resource.
     */
    public function show(College $college)
    {
        //
        return CollegeResource::make($college);

    }

    /**
     * Update the specified resource in storage.
     */
    public function update(CollegeUpdateRequest $request, College $college)
    {
        //
        $college->update($request->validated());
        return CollegeResource::make($college);
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(College $college)
    {
        try{
            $college->delete();
        } catch(Exception $e) {
            $college->update(['delete' => true]);
        }
        return response()->noContent();
    }
}
