<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('lib_positions', function (Blueprint $table) {
            $table->id();
            $table->string('desc');
            $table->unsignedBigInteger('sports_id');
            $table->foreign('sports_id')->references('id')->on('sports');
            $table->timestamps();
            
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('lib_positions');
    }
};
