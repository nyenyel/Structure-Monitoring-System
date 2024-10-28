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
        Schema::create('players', function (Blueprint $table) {
            $table->id();
            $table->string('student_no');
            $table->unsignedBigInteger('team_id');
            $table->unsignedBigInteger('position_id');
            $table->unsignedBigInteger('basic_information_id');
            $table->unsignedBigInteger(column: 'lib_player_status_id');
            $table->foreign('team_id')->references('id')->on('teams');
            $table->foreign('position_id')->references('id')->on('lib_positions');
            $table->foreign('basic_information_id')->references('id')->on('basic_information');
            $table->foreign('lib_player_status_id')->references('id')->on('lib_player_statuses');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('players');
    }
};
