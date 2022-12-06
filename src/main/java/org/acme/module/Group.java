package org.acme.module;

public record Group (
        String group_id,
        String group_name,
        String group_date,
        String group_admin_id,
        String group_desc
) {}
