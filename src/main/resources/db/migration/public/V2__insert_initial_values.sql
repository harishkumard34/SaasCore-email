INSERT INTO public.tenant_sequence_tracker (last_sequence) 
VALUES (10000);


CREATE INDEX idx_auth_tenant_id ON auth(tenant_id);
CREATE INDEX idx_tenants_tenant_id ON tenants(tenant_id);