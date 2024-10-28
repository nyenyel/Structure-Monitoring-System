<?php

namespace App\Models\Library;

use App\Models\User;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\HasMany;

class LibRole extends Model
{
    use HasFactory;
    protected $guarded = ['id'];
    protected $fillable = [
        'desc'
    ];
    public function users(): HasMany
    {
        return $this->hasMany(User::class, 'lib_role_id');
    }
}
