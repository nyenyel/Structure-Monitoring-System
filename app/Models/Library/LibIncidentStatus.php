<?php

namespace App\Models\Library;

use App\Models\Sports\Incident;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\HasMany;

class LibIncidentStatus extends Model
{
    use HasFactory;
    protected $guarded = ['id'];
    protected $fillable = [
        'desc'
    ];
    public function incident(): HasMany
    {
        return $this->hasMany(Incident::class, 'lib_incident_status_id');
    }
}
