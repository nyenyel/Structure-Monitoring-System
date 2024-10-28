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
        Schema::create('teams', function (Blueprint $table) {
            $table->id();
            $table->string('title');
            $table->string('logo')->nullable();
            $table->unsignedBigInteger('sports_id');
            $table->unsignedBigInteger('college_id');
            $table->unsignedBigInteger('lib_award_id')->default(1);
            $table->unsignedBigInteger('coach_id');
            $table->foreign('sports_id')->references('id')->on('sports');
            $table->foreign('college_id')->references('id')->on('colleges');
            $table->foreign('lib_award_id')->references('id')->on('lib_awards');
            $table->foreign('coach_id')->references('id')->on('basic_information');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('teams');
    }
};
