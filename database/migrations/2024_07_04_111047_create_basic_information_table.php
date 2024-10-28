<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('basic_information', function (Blueprint $table) {
            $table->id();
            $table->string('first_name');
            $table->string('middle_name');
            $table->string('last_name');
            $table->string('phone_no');
            $table->unsignedBigInteger('lib_gender_id');
            $table->foreign('lib_gender_id')->references('id')->on('lib_genders');
            $table->timestamps();
        });

        DB::table('basic_information')->insert([
            [
                'first_name' => 'System',
                'middle_name' => 'Admin', 
                'last_name' => 'Admin', 
                'phone_no' => '09123456789', 
                'lib_gender_id' => 1, 
                'created_at' => now(),
                'updated_at' => now(),
            ],
        ]);
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('basic_information');
    }
};
