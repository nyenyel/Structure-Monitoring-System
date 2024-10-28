<?php

namespace App\Providers;

use App\Services\SemaphoreService;
use Illuminate\Support\ServiceProvider;

class SemaphoreServiceProvider extends ServiceProvider
{
    /**
     * Register services.
     */
    public function register(): void
    {
        $this->app->singleton(SemaphoreServiceProvider::class, function($app) {
            return new SemaphoreService();
        });
    }

    /**
     * Bootstrap services.
     */
    public function boot(): void
    {
        //
    }
}
