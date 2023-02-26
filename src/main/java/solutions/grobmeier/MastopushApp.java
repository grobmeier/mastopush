package solutions.grobmeier;

import social.bigbone.MastodonClient;
import social.bigbone.MastodonRequest;
import social.bigbone.api.Pageable;
import social.bigbone.api.Range;
import social.bigbone.api.entity.Status;
import social.bigbone.api.exception.BigBoneRequestException;

public class MastopushApp {
    public static void main(String[] args) throws BigBoneRequestException {
        System.out.println("Hello World!");


        String accessToken = System.getenv("accessToken");
        if (accessToken == null) {
            System.out.println("No accessToken found in environment variables");
            System.out.println("Please set it adding accessToken to the enviornment variables");
            System.exit(1);
        }

        String instanceHostname = System.getenv("instance");
        if (instanceHostname == null) {
            System.out.println("No instanceHostname found in environment variables");
            System.out.println("Please set it adding instance to the enviornment variables");
            System.exit(1);
        }

        // TODO remove
        System.out.println("accessToken: " + accessToken);
        System.out.println("instance: " + instanceHostname);


        MastodonClient client = new MastodonClient.Builder(instanceHostname).accessToken(accessToken).build();
        Pageable<Status> timeline = client.timelines().getHomeTimeline(new Range(null, null, 5)).execute();

        timeline.getPart().forEach(status -> {
            System.out.println(status.getContent());
        });

        MastodonRequest<Status> request = client.statuses()
                .postStatus("Hello World",
                        null,
                        null,
                        false,
                        null,
                        Status.Visibility.Unlisted);
        Status status = request.execute();

    }
}
