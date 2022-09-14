package com.inveox.poc.grpc;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import io.grpc.examples.routeguide.Feature;
import io.grpc.examples.routeguide.Point;
import io.grpc.examples.routeguide.Rectangle;
import io.grpc.examples.routeguide.RouteGuide;
import io.grpc.examples.routeguide.RouteNote;
import io.grpc.examples.routeguide.RouteSummary;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@GrpcService
public class RouteGuideServer implements RouteGuide {

    private final Collection<Feature> features;
    private final ConcurrentMap<Point, List<RouteNote>> routeNotes = new ConcurrentHashMap<Point, List<RouteNote>>();

    public RouteGuideServer() throws IOException {
        URL url = RouteGuideUtil.getDefaultFeaturesFile();
        this.features = RouteGuideUtil.parseFeatures(url);
        System.out.println("Features loaded= "+this.features.size());
    }

    @Override
    public Uni<Feature> getFeature(Point request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Multi<Feature> listFeatures(Rectangle request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Uni<RouteSummary> recordRoute(Multi<Point> request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Multi<RouteNote> routeChat(Multi<RouteNote> request) {
        // TODO Auto-generated method stub
        return null;
    }

    private Feature checkFeature(Point location) {
        for (Feature feature : features) {
            if (feature.getLocation().getLatitude() == location.getLatitude()
                    && feature.getLocation().getLongitude() == location.getLongitude()) {
                return feature;
            }
        }

        // No feature was found, return an unnamed feature.
        return Feature.newBuilder().setName("").setLocation(location).build();
    }

}
