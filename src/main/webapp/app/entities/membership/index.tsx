import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Membership from './membership';
import MembershipDetail from './membership-detail';
import MembershipUpdate from './membership-update';
import MembershipDeleteDialog from './membership-delete-dialog';

const MembershipRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Membership />} />
    <Route path="new" element={<MembershipUpdate />} />
    <Route path=":id">
      <Route index element={<MembershipDetail />} />
      <Route path="edit" element={<MembershipUpdate />} />
      <Route path="delete" element={<MembershipDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MembershipRoutes;
