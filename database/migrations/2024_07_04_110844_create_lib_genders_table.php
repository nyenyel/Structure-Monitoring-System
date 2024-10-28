<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;
use Illuminate\Support\Facades\DB;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('lib_genders', function (Blueprint $table) {
            $table->id();
            $table->string('desc');
            $table->timestamps();
        });
        DB::table('lib_genders')->insert([
            ['desc' => 'Male'],
            ['desc' => 'Female'],
        ]);
    }
    
    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('lib_genders');
    }
};
