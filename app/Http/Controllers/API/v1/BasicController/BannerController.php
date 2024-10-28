<?php

namespace App\Http\Controllers\api\v1\BasicController;

use App\Http\Controllers\Controller;
use App\Http\Requests\Store\BannerStoreRequest;
use App\Http\Requests\Update\BannerUpdateRequest;
use App\Http\Resources\BannerResource;
use App\Models\Others\Banner;
use Exception;
use Illuminate\Http\Request;
use Illuminate\Support\Str;


class BannerController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        return BannerResource::collection(Banner::all());
    }


    /**
     * Store a newly created resource in storage.
     */
    public function store(BannerStoreRequest $request)
    {
        $validated = $request->validated();

        $img = $request->file('image'); // Correct way to access the file in an array

        // Get the file's extension
        $ext = $img->getClientOriginalExtension();

        // Generate a random filename
        $imageName = Str::random(20) . '.' . $ext;

        // Move the file to the 'image' directory
        $img->move(public_path('image'), $imageName);

        // Store the URL or path in the database
        $validated['image'] = asset('image/' . $imageName);

        $banner = Banner::create($validated);
        return BannerResource::make($banner);
    }

    /**
     * Display the specified resource.
     */
    public function show(Banner $banner)
    {
        $data = Banner::where('is_default', 1)->first();
        return BannerResource::make($data);
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(BannerUpdateRequest $request, Banner $banner)
    {
        try{
            $banner->update(['is_default'=> 1]);
            Banner::where('is_default', 1)->where('id', '!=', $banner->id)->update(['is_default' => 0]);
            return json_encode(['message'=>'Succefully updated']);
        } catch (Exception $e) {
            return json_encode(['error' => $e->getMessage()]);
        }
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(Banner $banner)
    {
        $banner->delete();
        return json_encode(['message' => 'Deleted Succesfully']);
    }
}
