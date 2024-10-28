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
        Schema::create('schedules', function (Blueprint $table) {
            $table->id();
            $table->date('date')->nullable();
            $table->time('time')->nullable();
            $table->integer('match_no');
            $table->string('match_round');
            $table->string('referee_full_name')->default('');
            $table->unsignedBigInteger('next_match_id')->nullable();
            $table->unsignedBigInteger('first_team_id')->nullable();
            $table->unsignedBigInteger('second_team_id')->nullable();
            $table->unsignedBigInteger('winner_team_id')->nullable();
            $table->unsignedBigInteger('sports_id');
            $table->unsignedBigInteger('lib_game_statuses_id');
            $table->foreign('next_match_id')->references('id')->on('schedules');
            $table->foreign('first_team_id')->references('id')->on('teams');
            $table->foreign('second_team_id')->references('id')->on('teams');
            $table->foreign('winner_team_id')->references('id')->on('teams');
            $table->foreign('sports_id')->references('id')->on('sports');
            $table->foreign('lib_game_statuses_id')->references('id')->on('lib_game_statuses');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('schedules');
    }
};
