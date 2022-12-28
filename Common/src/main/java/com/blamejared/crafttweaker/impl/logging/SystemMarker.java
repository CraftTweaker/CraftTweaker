package com.blamejared.crafttweaker.impl.logging;

import org.apache.logging.log4j.Marker;

final class SystemMarker implements Marker {
    
    private final String name;
    private final Marker marker;
    
    private SystemMarker(final String name, final Marker marker) {
        
        this.name = name;
        this.marker = marker;
    }
    
    static Marker of(final String name, final Marker marker) {
        
        return new SystemMarker(name, marker);
    }
    
    @Override
    public Marker addParents(final Marker... markers) {
        
        this.marker.addParents(markers);
        return this;
    }
    
    @Override
    public String getName() {
        
        return this.name;
    }
    
    @Override
    public Marker[] getParents() {
        
        return this.marker.getParents();
    }
    
    @Override
    public boolean hasParents() {
        
        return this.marker.hasParents();
    }
    
    @Override
    public boolean isInstanceOf(final Marker m) {
        
        return this.marker.isInstanceOf(m);
    }
    
    @Override
    public boolean isInstanceOf(final String name) {
        
        return this.marker.isInstanceOf(name) || this.name.equals(name);
    }
    
    @Override
    public boolean remove(final Marker marker) {
        
        return this.marker.remove(marker);
    }
    
    @Override
    public Marker setParents(final Marker... markers) {
        
        this.marker.setParents(markers);
        return this;
    }
    
}
