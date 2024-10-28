<?php

namespace App\Models\Library;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class LibPlayerStatus extends Model
{
    use HasFactory;

    protected $guarded = ['id'];
    protected $fillable = [
        'desc'
    ];
}
