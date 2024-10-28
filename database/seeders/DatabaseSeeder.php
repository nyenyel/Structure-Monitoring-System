<?php

namespace Database\Seeders;

use App\Models\Sports\College;
use App\Models\Sports\Sports;
use App\Models\User;
use Database\Factories\CollegeFactory;
// use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     */
    public function run(): void
    {
        // User::factory(10)->create();
        $this->call([
            LibrarySeeder::class
        ]);
        // College::factory(10)->create();
        // Sports::factory(10)->create();
        // User::factory()->create([
        //     'name' => 'Test User',
        //     'email' => 'test@example.com',
        // ]);
    }
}
