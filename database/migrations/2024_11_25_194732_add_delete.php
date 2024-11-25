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
        Schema::table('colleges', function (Blueprint $table) {
            $table->boolean('delete')->default(false);
        });
        Schema::table('sports', function (Blueprint $table) {
            $table->boolean('delete')->default(false);
        });
        Schema::table('lib_positions', function (Blueprint $table) {
            $table->boolean('delete')->default(false);
        });
        Schema::table('schedules', function (Blueprint $table) {
            $table->string('reason')->nullable()->default(null);
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::table('collges', function (Blueprint $table) {
            //
        });
    }
};
