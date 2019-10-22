package edu.iastate.coms309.project309;

public class Profile
{
    public int id;
    public String firstName;
    public String lastName;
    public String address;
    public String phone;
    public String username;
    public String password;
    public int points;

    public Profile()
    {
        mTextViewResult = findViewById(R.id.text_view_result);
        Button profile = findViewById(R.id.button_parse);

        mQueue = Volley.newRequestQueue(this);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });
    }

    private void jsonParse() {
        String url = "http://coms-309-ss-8.misc.iastate.edu:8080/owners/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Log.e("", response.toString());
                            JSONArray jsonArray = response.getJSONArray("profiles");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject user = jsonArray.getJSONObject(i);
                                String id = user.getString("id");
                                String firstName = user.getString("firstName");
                                String lastName = user.getString("lastName");
                                String address = user.getString("address");
                                String telephone = user.getString("telephone");
                                String username = user.getString("username");
                                String password = user.getString("password");
                                Integer points = user.getInt("points");

                                mTextViewResult.append(id+", "+firstName + ", " + lastName+", "+address+", "+telephone+", "+username+
                                        ", " +password+String.valueOf(points) +"\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}
